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

import com.gerritforge.gerrit.plugins.analytics.common.{CommitsStatistics, CommitsStatisticsCache, CommitsStatisticsCacheKey, CommitsStatisticsLoader}
import com.google.inject.Inject
import com.gerritforge.gerrit.plugins.analytics.common._
import org.eclipse.jgit.lib.ObjectId

trait TestCommitStatisticsNoCache {
  self: GerritTestDaemon =>

  lazy val commitsStatisticsNoCache  = CommitsStatisticsNoCache(daemonTest.getInstance(classOf[CommitsStatisticsLoader]))
}

case class CommitsStatisticsNoCache @Inject() (commitsStatisticsLoader: CommitsStatisticsLoader) extends CommitsStatisticsCache {
  override def get(project: String, objectId: ObjectId): CommitsStatistics =
    commitsStatisticsLoader.load(CommitsStatisticsCacheKey(project, objectId))
}
