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

import com.google.common.cache.LoadingCache
import com.google.inject.{ImplementedBy, Inject}
import com.google.inject.name.Named
import com.gerritforge.gerrit.plugins.analytics.common.CommitsStatisticsCache.COMMITS_STATISTICS_CACHE
import org.eclipse.jgit.lib.ObjectId

trait CommitsStatisticsCache {
  def get(project: String, objectId: ObjectId): CommitsStatistics
}

case class CommitsStatisticsCacheKey(projectName: String, commitId: ObjectId)

object CommitsStatisticsCache {
  final val COMMITS_STATISTICS_CACHE = "commits_statistics_cache"
}

class CommitsStatisticsCacheImpl @Inject() (@Named(COMMITS_STATISTICS_CACHE) commitStatsCache: LoadingCache[CommitsStatisticsCacheKey, CommitsStatistics]
) extends CommitsStatisticsCache {

  override def get(project: String, objectId: ObjectId): CommitsStatistics =
    commitStatsCache.get(CommitsStatisticsCacheKey(project, objectId))
}