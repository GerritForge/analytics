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

import com.gerritforge.gerrit.plugins.analytics.AnalyticsConfig

object TestAnalyticsConfig extends AnalyticsConfig {
  val IGNORED_FILE_SUFFIX                  = ".bin"
  val botlikeFilenameRegexps: List[String] = List.empty
  val isExtractIssues: Boolean             = true
  val ignoreFileSuffixes: List[String]     = List(IGNORED_FILE_SUFFIX)
}
