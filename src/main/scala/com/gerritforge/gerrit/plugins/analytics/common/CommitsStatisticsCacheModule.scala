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

import com.google.gerrit.server.cache.CacheModule

class CommitsStatisticsCacheModule extends CacheModule() {

  override protected def configure(): Unit = {
    bind(classOf[CommitsStatisticsCache]).to(classOf[CommitsStatisticsCacheImpl])
    persist(CommitsStatisticsCache.COMMITS_STATISTICS_CACHE, classOf[CommitsStatisticsCacheKey], classOf[CommitsStatistics])
      .version(1)
      .diskLimit(-1)
      .maximumWeight(100000)
      .keySerializer(CommitsStatisticsCacheKeySerializer)
      .valueSerializer(CommitsStatisticsCacheSerializer)
      .loader(classOf[CommitsStatisticsLoader])
  }
}
