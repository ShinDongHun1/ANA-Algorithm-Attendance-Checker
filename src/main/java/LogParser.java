import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogParser {

    public static void main(String[] args) throws GitAPIException, IOException {
        final List<Member> members = parseLog(RepositoryUtil.getRepo("D:\\알고리즘\\하루하나알고리즘\\2021-algorithm-study"));

        ReadmeMaker.makeAllMonthReadMe(members);

    }

    public static List<Member> parseLog(Repository repository) throws GitAPIException, IOException {

            try (Git git = new Git(repository)) {
                Iterable<RevCommit> logs = git.log().call();

                logs = git.log()
                        .add(repository.resolve("remotes/origin/main"))
                        .call();

                List<CommitLog> commitLogList = new ArrayList<>();
                for (RevCommit rev : logs) {
                    commitLogList.add(getConventionalCommitMessage(rev));
                }

                Map<Member, List<CommitLog>> memberListMap = commitLogList.stream().collect(Collectors.groupingBy(CommitLog::getMember));
                List<Member> result = new ArrayList<>();
                memberListMap.keySet().stream().forEach(member -> {
                    member.setCommitLogList(memberListMap.get(member));
                    result.add(member);
                });

                return result;
            }

    }


    private static CommitLog getConventionalCommitMessage(RevCommit commit) {
        StringBuilder stringBuilder = new StringBuilder();

        // Prepare the pieces
        final String justTheAuthorNoTime = commit.getAuthorIdent().toExternalString().split(">")[0] + ">";

        final Instant commitInstant = Instant.ofEpochSecond(commit.getCommitTime());
        final ZoneId zoneId = commit.getAuthorIdent().getTimeZone().toZoneId();

        ZonedDateTime authorDateTime = ZonedDateTime.ofInstant(commitInstant, zoneId);

        final String minute = authorDateTime.format(DateTimeFormatter.ofPattern("Z")).replace("+", "").replace("0", "");
        authorDateTime= authorDateTime.minusMinutes(Integer.parseInt(minute));

        final String gitDateTimeFormatString = "yyyy:MMM:dd HH:mm:ss";
        final String formattedDate = authorDateTime.format(DateTimeFormatter.ofPattern(gitDateTimeFormatString));
        final String tabbedCommitMessage =
                Arrays.stream(commit.getFullMessage()
                                .split("\\r?\\n")) // split it up by line
                        .map(s -> "\t" + s + "\n") // add a tab on each line
                        .collect(Collectors.joining()); // put it back together



        // Put pieces together
        stringBuilder
                //.append("commit ").append(commit.getName()).append("\n")
                .append("Author:\t").append(justTheAuthorNoTime).append("\n")
                .append("Date:\t").append(formattedDate).append("\n")
                .append(tabbedCommitMessage.trim())
                .append("\n");




        String username = justTheAuthorNoTime.split("<")[0].trim();
        String email = justTheAuthorNoTime.split("<")[1].trim();
        if(email.contains("+")){
            email = email.trim().substring(email.indexOf("+")).replace(">","");
        }else {
            email = email.trim().replace(">","");
        }
        Member member = Member.ofUsernameAndEmail(username, email);
        CommitLog commitLog = CommitLog.ofMessageAndTime(tabbedCommitMessage, LocalDateTime.parse(formattedDate, DateTimeFormatter.ofPattern("yyyy:MMM:dd HH:mm:ss")));

        commitLog.setMember(member);
        return commitLog;
    }
}
