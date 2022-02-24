import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.util.List;

public class ReadmeMaker {

    private final static int YEAR = 2022;

   public final static String FIRST_FORMAT = "| 참여자 | 1일 | 2일 | 3일 | 4일 | 5일 | 6일 | 7일 | 8일 | 9일 | 10일 | 11일 | 12일 | 13일 | 14일 | 15일 | 16일 | 17일 | 18일 | 19일 | 20일 | 21일 | 22일 | 23일 | 24일 | 25일 | 26일 | 27일 | 28일 | 29일 | 30일 | 31일 | 횟수 |";
   public final static String SECOND_FORMAT = "| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |";


   public final static String CHECK_FORMAT = ":white_check_mark:";

   public static void makeAllMonthReadMe( List<Member> memberList){
       for (int i = 1; i <= 12; i++) {
           makeReadMeForMonth(i, memberList);
       }
   }



    private static void makeReadMeForMonth(int month, List<Member> memberList) {

        File file = new File(month+"_README.md");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("\n");//줄바꿈
            bw.write(month+"월");
            bw.write("\n");

            bw.write(FIRST_FORMAT);
            bw.write("\n");
            bw.write(SECOND_FORMAT);
            bw.write("\n");


            for (Member member : memberList) {
                String format = "|{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}|{10}|{11}|{12}|{13}|{14}|{15}|{16}|{17}|{18}|{19}|{20}|{21}|{22}|{23}|{24}|{25}|{26}|{27}|{28}|{29}|{30}|{31}|{32}|";
                format = format.replace("{0}", member.getUsername());

                member.sortCommitList();

                int count =0;
                final List<CommitLog> commitLogList = member.getCommitLogList().stream().distinct().toList();
                for (CommitLog commitLog : commitLogList) {
                    if(! (commitLog.getLocalDateTime().getMonth().getValue() == month) || YEAR != commitLog.getLocalDateTime().getYear()) continue;
                    if(commitLog.getMember().getUsername().equals("ShinDongHun1")){
                        System.out.println(commitLog.getLocalDateTime());
                    }
                    format = format.replace("{"+commitLog.getLocalDateTime().getDayOfMonth()+"}",CHECK_FORMAT);
                    count++;
                }

                format = format.replace("{32}",count+"");
                bw.write(change(format));
                bw.write("\n");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String change(String format){
       for(int i = 1; i <=31; i++){
           format = format.replace("{"+i+"}", "");
       }
       return format;
   }

}
