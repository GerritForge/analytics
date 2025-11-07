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

import com.google.gerrit.json.OutputFormat
import com.google.gson._
import com.google.inject.Singleton

import java.io.PrintWriter
import java.lang.reflect.Type

@Singleton
class GsonFormatter {
  val gsonBuilder: GsonBuilder =
    OutputFormat.JSON_COMPACT.newGsonBuilder
      .registerTypeHierarchyAdapter(classOf[Iterable[Any]], new IterableSerializer)
      .registerTypeHierarchyAdapter(classOf[Option[Any]], new OptionSerializer())

  def format[T](values: IterableOnce[T], out: PrintWriter) = {
    val gson: Gson = gsonBuilder.create

    values.iterator.foreach {value =>
      gson.toJson(value, out)
      out.println()
    }
  }

  class IterableSerializer extends JsonSerializer[Iterable[Any]] {
    override def serialize(src: Iterable[Any], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
      import scala.jdk.CollectionConverters._
      context.serialize(src.asJava)
    }
  }

  class OptionSerializer extends JsonSerializer[Option[Any]] {
    def serialize(src: Option[Any], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
      src match {
        case None => JsonNull.INSTANCE
        case Some(v) => context.serialize(v)
      }
    }
  }
}
