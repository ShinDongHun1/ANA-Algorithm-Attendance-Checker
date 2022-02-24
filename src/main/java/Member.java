import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Member {

    private String username;
    private String email;
    private List<CommitLog> commitLogList;


    private Member(String username, String email){
        this.username = username;
        this.email = email;
    }
    public static Member ofUsernameAndEmail(String username, String email){
        return new Member(username, email);
    }

    public void setCommitLogList(List<CommitLog> commitLogList) {
        this.commitLogList = commitLogList;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<CommitLog> getCommitLogList() {
        return commitLogList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return username.equals(member.username) && email.equals(member.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }

    @Override
    public String toString() {
        return "Member{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void sortCommitList() {
        commitLogList.sort(Comparator.naturalOrder());
    }
}
