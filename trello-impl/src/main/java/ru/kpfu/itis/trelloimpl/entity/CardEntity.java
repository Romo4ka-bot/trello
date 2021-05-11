package ru.kpfu.itis.trelloimpl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Roman Leontev
 * 11:16 10.04.2021
 * group 11-905
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "card")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    private ListCardEntity listCard;

    @ManyToOne
    private UserEntity user;

    @OneToMany(mappedBy = "card")
    private List<AttachmentEntity> attachments;

    @OneToMany(mappedBy = "card")
    private List<CheckListEntity> checkLists;

    @OneToMany(mappedBy = "card")
    private List<CommentEntity> comments;
}
