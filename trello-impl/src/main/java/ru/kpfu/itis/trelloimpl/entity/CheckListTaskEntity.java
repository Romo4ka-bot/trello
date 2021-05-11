package ru.kpfu.itis.trelloimpl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Roman Leontev
 * 13:19 09.05.2021
 * group 11-905
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "check_list_task")
public class CheckListTaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Boolean done;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private CheckListEntity checkList;
}
