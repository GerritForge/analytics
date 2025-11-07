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

import com.gerritforge.gerrit.plugins.analytics.AnalyticsConfig
import com.google.inject.{Inject, Singleton}
import org.eclipse.jgit.treewalk.TreeWalk
import org.eclipse.jgit.treewalk.filter.TreeFilter
import org.gitective.core.PathFilterUtils

@Singleton
case class IgnoreFileSuffixFilter @Inject() (config: AnalyticsConfig) extends TreeFilter {

  private lazy val suffixFilter =
    if (config.ignoreFileSuffixes.nonEmpty)
      PathFilterUtils.orSuffix(config.ignoreFileSuffixes:_*).negate()
    else
      TreeFilter.ALL

  override def include(treeWalk: TreeWalk): Boolean = treeWalk.isSubtree || suffixFilter.include(treeWalk)
  override def shouldBeRecursive(): Boolean = suffixFilter.shouldBeRecursive()
  override def clone(): TreeFilter = this
}
