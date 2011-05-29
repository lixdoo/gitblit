/*
 * Copyright 2011 gitblit.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gitblit.tests;

import junit.framework.TestCase;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import com.gitblit.utils.DiffUtils;
import com.gitblit.utils.JGitUtils;
import com.gitblit.utils.JGitUtils.DiffOutputType;

public class DiffUtilsTest extends TestCase {

	public void testParentCommitDiff() throws Exception {
		Repository repository = GitBlitSuite.getHelloworldRepository();
		RevCommit commit = JGitUtils.getCommit(repository,
				"1d0c2933a4ae69c362f76797d42d6bd182d05176");
		String diff = DiffUtils.getCommitDiff(repository, commit, DiffOutputType.PLAIN);
		repository.close();
		assertTrue(diff != null && diff.length() > 0);
		String expected = "-		system.out.println(\"Hello World\");\n+		System.out.println(\"Hello World\"";
		assertTrue(diff.indexOf(expected) > -1);
	}

	public void testArbitraryCommitDiff() throws Exception {
		Repository repository = GitBlitSuite.getHelloworldRepository();
		RevCommit baseCommit = JGitUtils.getCommit(repository,
				"8baf6a833b5579384d9b9ceb8a16b5d0ea2ec4ca");
		RevCommit commit = JGitUtils.getCommit(repository,
				"1d0c2933a4ae69c362f76797d42d6bd182d05176");
		String diff = DiffUtils.getDiff(repository, baseCommit, commit, DiffOutputType.PLAIN);
		repository.close();
		assertTrue(diff != null && diff.length() > 0);
		String expected = "-		system.out.println(\"Hello World\");\n+		System.out.println(\"Hello World\"";
		assertTrue(diff.indexOf(expected) > -1);
	}

	public void testPlainFileDiff() throws Exception {
		Repository repository = GitBlitSuite.getHelloworldRepository();
		RevCommit commit = JGitUtils.getCommit(repository,
				"1d0c2933a4ae69c362f76797d42d6bd182d05176");
		String diff = DiffUtils.getDiff(repository, commit, "java.java", DiffOutputType.PLAIN);
		repository.close();
		assertTrue(diff != null && diff.length() > 0);
		String expected = "-		system.out.println(\"Hello World\");\n+		System.out.println(\"Hello World\"";
		assertTrue(diff.indexOf(expected) > -1);
	}

	public void testFilePatch() throws Exception {
		Repository repository = GitBlitSuite.getHelloworldRepository();
		RevCommit commit = JGitUtils.getCommit(repository,
				"1d0c2933a4ae69c362f76797d42d6bd182d05176");
		String patch = DiffUtils.getCommitPatch(repository, null, commit, "java.java");
		repository.close();
		assertTrue(patch != null && patch.length() > 0);
		String expected = "-		system.out.println(\"Hello World\");\n+		System.out.println(\"Hello World\"";
		assertTrue(patch.indexOf(expected) > -1);
	}

	public void testArbitraryFilePatch() throws Exception {
		Repository repository = GitBlitSuite.getHelloworldRepository();
		RevCommit baseCommit = JGitUtils.getCommit(repository,
				"8baf6a833b5579384d9b9ceb8a16b5d0ea2ec4ca");
		RevCommit commit = JGitUtils.getCommit(repository,
				"1d0c2933a4ae69c362f76797d42d6bd182d05176");
		String patch = DiffUtils.getCommitPatch(repository, baseCommit, commit, "java.java");
		repository.close();
		assertTrue(patch != null && patch.length() > 0);
		String expected = "-		system.out.println(\"Hello World\");\n+		System.out.println(\"Hello World\"";
		assertTrue(patch.indexOf(expected) > -1);
	}

	public void testArbitraryCommitPatch() throws Exception {
		Repository repository = GitBlitSuite.getHelloworldRepository();
		RevCommit baseCommit = JGitUtils.getCommit(repository,
				"8baf6a833b5579384d9b9ceb8a16b5d0ea2ec4ca");
		RevCommit commit = JGitUtils.getCommit(repository,
				"1d0c2933a4ae69c362f76797d42d6bd182d05176");
		String patch = DiffUtils.getCommitPatch(repository, baseCommit, commit, null);
		repository.close();
		assertTrue(patch != null && patch.length() > 0);
		String expected = "-		system.out.println(\"Hello World\");\n+		System.out.println(\"Hello World\"";
		assertTrue(patch.indexOf(expected) > -1);
	}
}
