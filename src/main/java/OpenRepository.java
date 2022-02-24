
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;


import java.io.File;
import java.io.IOException;

/**
 * Simple snippet which shows how to open an existing repository
 *
 * @author dominik.stadler at gmx.at
 */
public class OpenRepository {

    public static Repository getRepo() throws IOException, GitAPIException {
        // first create a test-repository, the return is including the .get directory here!
        File repoDir =  new File("D:\\알고리즘\\하루하나알고리즘\\2021-algorithm-study", ".git");

        // now open the resulting repository with a FileRepositoryBuilder
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try (Repository repository = builder.setGitDir(repoDir)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build()) {
            /*System.out.println("Having repository: " + repository.getDirectory());

            // the Ref holds an ObjectId for any type of object (tree, commit, blob, tree)

            Ref head = repository.exactRef("refs/heads/main");

            System.out.println("Ref of refs/heads/main: " + head);*/
            return repository;
        }


    }



}