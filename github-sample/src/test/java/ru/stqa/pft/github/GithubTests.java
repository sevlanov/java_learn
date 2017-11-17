package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

public class GithubTests {
    @Test
    public void testCommits() {

        Github github = new RtGithub("dc350d26e282a6ca36e16ce89695d4eb01ff29b5");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("sevlanov", "java_learn")).commits();
        for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(commit);
        }

    }
}
