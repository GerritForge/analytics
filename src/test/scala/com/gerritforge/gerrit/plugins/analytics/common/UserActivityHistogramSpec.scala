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

import com.gerritforge.gerrit.plugins.analytics.common.{AggregatedHistogramFilterByDates, UserActivityHistogram}
import com.google.gerrit.acceptance.UseLocalDisk
import com.google.gerrit.testing.NoGitRepositoryCheckIfClosed
import com.gerritforge.gerrit.plugins.analytics.common.AggregationStrategy.EMAIL_YEAR
import com.gerritforge.gerrit.plugins.analytics.test.GerritTestDaemon
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

@UseLocalDisk
@NoGitRepositoryCheckIfClosed
class UserActivityHistogramSpec extends AnyFlatSpecLike with Matchers with GerritTestDaemon {

  "UserActivityHistogram" should "return no activities" in {
    val filter = new AggregatedHistogramFilterByDates(aggregationStrategy = EMAIL_YEAR)
    new UserActivityHistogram().get(testFileRepository.getRepository, filter) should have size 0
  }

  it should "aggregate to one activity" in {
    testFileRepository.commitFile("test.txt", "content")
    val filter = new AggregatedHistogramFilterByDates(aggregationStrategy = EMAIL_YEAR)
    new UserActivityHistogram().get(testFileRepository.getRepository, filter) should have size 1
  }

}
