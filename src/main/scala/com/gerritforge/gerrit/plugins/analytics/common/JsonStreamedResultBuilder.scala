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

import com.google.gerrit.extensions.restapi.BinaryResult

import java.io.{OutputStream, PrintWriter}
import scala.util.Using

class GsonStreamedResult[T](val jsonFmt: GsonFormatter,
                            val committers: IterableOnce[T]) extends BinaryResult {
  override def writeTo(os: OutputStream) =
    Using.resource(new PrintWriter(os))(jsonFmt.format(committers, _))
}
