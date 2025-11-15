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

import com.google.gerrit.extensions.restapi.PreconditionFailedException
import com.google.inject.Singleton
import org.eclipse.jgit.lib.Repository
import org.gitective.core.CommitFinder

@Singleton
class UserActivityHistogram {
  def get(repo: Repository, filter: AbstractCommitHistogramFilter) = {
    val finder = new CommitFinder(repo)

    try {
      finder.setFilter(filter).find
      val histogram = filter.getHistogram
      histogram.getAggregatedUserActivity
    } catch {
      // 'find' throws an IllegalArgumentException when the conditions to walk through the commits tree are not met,
      // i.e: an empty repository doesn't have the starting commit.
      case _: IllegalArgumentException => Array.empty[AggregatedUserCommitActivity]
      case e: Exception                =>
        throw new PreconditionFailedException(s"Cannot find commits: ${e.getMessage}").initCause(e)
    }
  }
}
