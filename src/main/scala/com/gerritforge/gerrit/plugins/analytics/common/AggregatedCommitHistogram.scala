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

import com.gerritforge.gerrit.plugins.analytics.common.AggregationStrategy.{AggregationKey, BY_BRANCH}
import org.eclipse.jgit.lib.PersonIdent
import org.eclipse.jgit.revwalk.RevCommit
import org.gitective.core.stat.{CommitHistogram, CommitHistogramFilter, UserCommitActivity}

import java.util.Date

class AggregatedUserCommitActivity(val key: AggregationKey, val name: String, val email: String)
  extends UserCommitActivity(name, email)

class AggregatedCommitHistogram(var aggregationStrategy: AggregationStrategy)
  extends CommitHistogram {

  def includeWithBranches(commit: RevCommit, user: PersonIdent, branches: Set[String]): Unit = {
    branches.foreach { branch =>
      val originalStrategy = aggregationStrategy
      this.aggregationStrategy = BY_BRANCH(branch, aggregationStrategy)
      this.include(commit, user)
      this.aggregationStrategy = originalStrategy
    }
  }

  override def include(commit: RevCommit, user: PersonIdent): AggregatedCommitHistogram = {
    val key = aggregationStrategy.mapping(user, commit.getAuthorIdent.getWhen)
    val keyString = key.toString

    val activity = Option(users.get(keyString)) match {
      case None =>
        val newActivity = new AggregatedUserCommitActivity(key,
          user.getName, user.getEmailAddress)
        users.put(keyString, newActivity)
        newActivity
      case Some(foundActivity) => foundActivity
    }
    activity.include(commit, user)
    this
  }

  def getAggregatedUserActivity: Array[AggregatedUserCommitActivity] = {
    users.values.toArray(new Array[AggregatedUserCommitActivity](users.size))
  }
}

object AggregatedCommitHistogram {
  type AggregationStrategyMapping = (PersonIdent, Date) => AggregationKey
}

abstract class AbstractCommitHistogramFilter(aggregationStrategy: AggregationStrategy)
  extends CommitHistogramFilter {
  val AbstractHistogram = new AggregatedCommitHistogram(aggregationStrategy)

  override def getHistogram = AbstractHistogram
}
