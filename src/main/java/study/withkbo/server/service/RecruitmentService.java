package study.withkbo.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RecruitmentService {
    // 특정 채팅방에 브로드캐스트
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public RecruitmentService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 추가적으로 특정 채팅방에 메시지 전송
    public void afterRecruitedChatRoom(String roomName, String username) {
        this.messagingTemplate.convertAndSend("/topic/" + roomName, username + " 님이 입장하셨습니다.");
    }
}
