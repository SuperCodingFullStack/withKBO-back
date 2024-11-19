package study.withkbo.exception.common;


import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private final CommonError commonError;

    public CommonException(CommonError errorCode) {
        super(errorCode.getMessage());
        this.commonError = errorCode;
    }



}
