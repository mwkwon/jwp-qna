package qna.domain.wrappers;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Deleted {

    @Column(nullable = false)
    private boolean deleted;

    protected Deleted() {
    }

    public Deleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void delete(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deleted deleted1 = (Deleted) o;
        return deleted == deleted1.deleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deleted);
    }

    @Override
    public String toString() {
        return "deleted=" + deleted;
    }
}
