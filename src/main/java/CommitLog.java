import java.time.LocalDateTime;
import java.util.Objects;

public class CommitLog implements Comparable<CommitLog>{

    private Member member;
    private String message;
    private LocalDateTime localDateTime;


    private CommitLog(String message, LocalDateTime localDateTime) {
        this.message = message;
        this.localDateTime = localDateTime;
    }

    public static CommitLog ofMessageAndTime(String message, LocalDateTime localDateTime){
        return new CommitLog(message, localDateTime);
    }


    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }


    public String getMessage() {
        return message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString() {
        return "CommitLog{ " +
                "\n"+ member +
                "\n     message='" + message + '\'' +
                "\n    localDateTime=" + localDateTime +
                "\n}";
    }

    @Override
    public int compareTo(CommitLog o) {
        if(this.localDateTime.isAfter(o.localDateTime)) return 1;
        if(this.localDateTime.isBefore(o.localDateTime)) return -1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        CommitLog commitLog = (CommitLog) o;
        return (commitLog.getLocalDateTime().getYear()==localDateTime.getYear()) && (commitLog.getLocalDateTime().getMonth().equals(localDateTime.getMonth()))
                &&(commitLog.getLocalDateTime().getDayOfMonth() == localDateTime.getDayOfMonth()) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLocalDateTime().getYear()+"w"+getLocalDateTime().getMonth().getValue()+"w"+getLocalDateTime().getDayOfMonth());
    }
}
