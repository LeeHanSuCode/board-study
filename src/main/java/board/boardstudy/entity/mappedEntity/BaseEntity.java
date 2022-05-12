package board.boardstudy.entity.mappedEntity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    protected LocalDateTime createdDate;
    protected LocalDateTime updatedDate;
}
