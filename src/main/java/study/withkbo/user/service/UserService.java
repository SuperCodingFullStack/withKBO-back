package study.withkbo.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.team.repository.TeamRepository;
import study.withkbo.user.dto.request.UserPasswordRequestDto;
import study.withkbo.user.dto.request.UserSignUpRequestDto;
import study.withkbo.user.dto.request.UserUpdateRequestDto;
import study.withkbo.user.dto.response.UserResponseDto;
import study.withkbo.user.entity.User;
import study.withkbo.user.repository.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;
    private final S3Client s3Client;
    private final String s3BucketName;



    public UserResponseDto signUp(UserSignUpRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        checkUsername(username);

        User user = userRepository.save(User.builder()
                .username(username)
                .password(password)
                .nickname(requestDto.getNickname())
                .phone(requestDto.getPhone())
                .address(requestDto.getAddress())
                .profileImg(requestDto.getProfileImg())
                .team(requestDto.getTeam()).build());

        return new UserResponseDto(user);
    }

    public void checkPassword(String inputPassword, String password) {
        if(!passwordEncoder.matches(inputPassword, password)) {
            throw new CommonException(CommonError.USER_PASSWORD_WRONG);
        }
    }

    @Transactional
    public UserResponseDto updatePassword(UserPasswordRequestDto requestDto, User user) {
        checkPassword(requestDto.getCheckPassword(), user.getPassword());
        user.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public void checkUsername(String username) {
        Optional<User> checkUsername = userRepository.findByUsernameAndIsDeletedFalse(username);
        if (checkUsername.isPresent()) {
            throw new CommonException(CommonError.USER_ALREADY_EXIST_USERNAME);
        }
    }

    public void withdraw(User user) {
        user.withdraw();
        userRepository.save(user);
    }

    public void checkNickname(String nickname) {
        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if(checkNickname.isPresent()) {
            throw new CommonException(CommonError.USER_ALREADY_EXIST_USERNAME);
        }
    }

    public UserResponseDto updateUserInfo(UserUpdateRequestDto requestDto, User user) {
        user.updateUser(requestDto);
        return new UserResponseDto(userRepository.save(user));
    }

    public String uploadImage(MultipartFile image) {
        if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new CommonException(CommonError.NOT_FOUND);
        }
        return this.uploadFile(image);
    }

    private String uploadFile(MultipartFile image) {
        this.validateImageFileExtension(Objects.requireNonNull(image.getOriginalFilename()));
        try {
            return this.uploadImageToS3(image);
        } catch (Exception e) {
            throw new CommonException(CommonError.BAD_REQUEST);
        }
    }

    private void validateImageFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if(lastDotIndex == -1) {
            throw new CommonException(CommonError.BAD_REQUEST);
        }
        String extension = fileName.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtensionList = Arrays.asList("jpg","jpeg","png","gif");

        if(!allowedExtensionList.contains(extension)) {
            throw new CommonException(CommonError.BAD_REQUEST);
        }
    }
    private String uploadImageToS3(MultipartFile image) throws IOException {
        String originalFileName = image.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String s3FileName = UUID.randomUUID().toString().substring(0,10) + originalFileName;

        InputStream inputStream = image.getInputStream();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(s3BucketName).key(s3FileName).contentType("image/" + extension).build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, image.getSize()));

        } catch(Exception e) {
            throw new CommonException(CommonError.BAD_REQUEST);
        } finally {
            inputStream.close();
        }

        return s3Client.utilities().getUrl(b -> b.bucket(s3BucketName).key(s3FileName)).toExternalForm();
    }
}
