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

package com.gerritforge.gerrit.plugins.analytics.test
import com.gerritforge.gerrit.plugins.analytics.CommitInfo
import com.gerritforge.gerrit.plugins.analytics.common.GsonFormatter
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers
class CommitInfoSpec extends AnyFlatSpecLike with Matchers {
  "CommitInfo" should "be serialised as JSON correctly" in {
    val commitInfo = CommitInfo(sha1 = "sha", date = 1000L, merge = false, botLike = false, files = Set("file1", "file2"))
    val gsonBuilder = new GsonFormatter().gsonBuilder
    val actual = gsonBuilder.create().toJson(commitInfo)
    List(actual) should contain oneOf(
      "{\"sha1\":\"sha\",\"date\":1000,\"merge\":false,\"bot_like\":false,\"files\":[\"file1\",\"file2\"]}",
      "{\"sha1\":\"sha\",\"date\":1000,\"merge\":false,\"bot_like\":false,\"files\":[\"file2\",\"file1\"]}"
    )
  }
}
