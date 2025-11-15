// Copyright (C) 2025 GerritForge, Inc.
//
// Licensed under the BSL 1.1 (the "License");
// you may not use this file except in compliance with the License.
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.gerritforge.gerrit.plugins.analytics.common

import com.google.gerrit.acceptance.UseLocalDisk
import com.google.gerrit.testing.NoGitRepositoryCheckIfClosed
import com.gerritforge.gerrit.plugins.analytics.test.GerritTestDaemon
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

@UseLocalDisk
@NoGitRepositoryCheckIfClosed
class BranchesExtractorSpec extends AnyFlatSpecLike with Matchers with GerritTestDaemon {
  def commitsBranches = new BranchesExtractor(testFileRepository.getRepository)

  behavior of "branchesOfCommit"

  it should "extract one branch for a commit existing only in one branch" in {
    testFileRepository.commitFile("file", "content")
    testFileRepository.branch("feature/branch", "master")
    val commit =
      testFileRepository.commitFile("fileOnBranch", "content2", branch = "feature/branch")

    commitsBranches.branchesOfCommit(commit) shouldBe Set("feature/branch")
  }

  it should "extract two branches for a commit existing in two different branches" in {
    val commit = testFileRepository.commitFile("file", "content")
    testFileRepository.branch("feature/branch", "master")
    testFileRepository.commitFile("fileOnBranch", "content2", branch = "feature/branch")

    commitsBranches.branchesOfCommit(commit) shouldBe Set("feature/branch", "master")
  }
}
