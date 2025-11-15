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

import com.google.gerrit.server.project.ProjectResource
import com.google.gerrit.server.restapi.project.ProjectsCollection
import org.kohsuke.args4j.Argument

trait ProjectResourceParser {
  def projects: ProjectsCollection

  var projectRes: ProjectResource = null

  @Argument(usage = "project name", metaVar = "PROJECT", required = true)
  def setProject(project: String): Unit = {
    try {
      this.projectRes = projects.parse(project)
    } catch {
      case e: Exception =>
        throw new IllegalArgumentException("Error while trying to access project " + project, e)
    }
  }
}
