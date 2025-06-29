package com.hrowlxb.passy_backend.site.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "saved_site")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedSite {

    @Id
    private String id;

    private String email;
    private String siteName;
    private String loginId;
    private String loginPw;
}
