package com.example.oauth2;

//import java.sql.Timestamp;
//import javax.persistence.Entity;
//import javax.persistence.Id;

//import org.hibernate.annotations.CreationTimestamp;

//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.Entity;
//import org.hibernate.annotations.Table;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.annotation.Id;
//
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import java.sql.Timestamp;

//@Data
//@Entity

@SpringBootApplication
public class Oauth2Application {
//    @Id
//    private String userId;
//    private String password;
//    private String userName;
//    private String userRole;
//    @CreationTimestamp
//    private Timestamp regDt;

    public static void main(String[] args) {
        SpringApplication.run(Oauth2Application.class, args);


    }
}

//@ToString
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//class Memo {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //MySQL의 AUTO_INCREMENT를 사용
//    private Long id;
//    @Column(length = 200, nullable = false)
//    private String memoText;
//}