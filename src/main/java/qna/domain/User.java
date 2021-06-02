package qna.domain;

import qna.UnAuthorizedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user",
        uniqueConstraints = @UniqueConstraint(name = "UK_a3imlf41l37utmxiquukk8ajc", columnNames = "user_id"))
public class User extends BaseEntity {
    public static final GuestUser GUEST_USER = new GuestUser();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 50)
    private String email;

    @OneToMany(mappedBy = "writer")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "deletedBy")
    private List<DeleteHistory> deleteHistories = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private List<Question> questions = new ArrayList<>();

    protected User() {
    }

    public User(String userId, String password, String name, String email) {
        this(null, userId, password, name, email);
    }

    public User(Long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void update(User loginUser, User target) {
        if (!matchUserId(loginUser.userId)) {
            throw new UnAuthorizedException();
        }

        if (!matchPassword(target.password)) {
            throw new UnAuthorizedException();
        }

        this.name = target.name;
        this.email = target.email;
    }

    private boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean matchPassword(String targetPassword) {
        return this.password.equals(targetPassword);
    }

    public boolean equalsNameAndEmail(User target) {
        if (Objects.isNull(target)) {
            return false;
        }

        return name.equals(target.name) &&
                email.equals(target.email);
    }

    public boolean isGuestUser() {
        return false;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        if (!answers.contains(answer)) {
            answers.add(answer);
        }
        answer.toWriter(this);
    }

    public List<DeleteHistory> getDeleteHistories() {
        return deleteHistories;
    }

    public void addDeleteHistories(DeleteHistory deleteHistory) {
        if (!deleteHistories.contains(deleteHistory)) {
            deleteHistories.add(deleteHistory);
        }
        deleteHistory.toDeletedBy(this);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        if (!questions.contains(question)) {
            questions.add(question);
        }
        question.toWriter(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    private static class GuestUser extends User {
        @Override
        public boolean isGuestUser() {
            return true;
        }
    }
}
