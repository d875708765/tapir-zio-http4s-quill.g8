package utils.data_source

import io.circe.{Json, JsonObject, parser}
import io.getquill.context.ContextEffect
import io.getquill.context.qzio.ZioJdbcUnderlyingContext
import io.getquill.{PostgresDialect, PostgresZioJdbcContext, SnakeCase}
import zio.ZIO

import java.time.{Instant, LocalDateTime}

object DataSource {

  object PostgresQuillContext extends PostgresZioJdbcContext(SnakeCase) {

    override val underlying
        : ZioJdbcUnderlyingContext[PostgresDialect, SnakeCase.type] =
      new PostgresZioJdbcContext.Underlying[SnakeCase.type](naming) {
        override protected val effect: ContextEffect[Result] =
          new ContextEffect[Result] {
            override def wrap[T](t: => T): Result[T] =
              throw new IllegalArgumentException(
                "Runner not used for zio context."
              )

            override def push[A, B](
                result: Result[A]
            )(
                f: A => B
            ): Result[B] =
              result.map(f)

            override def seq[A](
                f: List[Result[A]]
            ): Result[List[A]] =
              ZIO.collectAll(f)
          }
      }

    implicit val jsonEncoder: Encoder[Json] = encoder(
      java.sql.Types.OTHER,
      (index, json, row) => {
        val pgObj = new org.postgresql.util.PGobject()
        pgObj.setType("jsonb")
        pgObj.setValue(json.noSpaces)
        row.setObject(index, pgObj)
      }
    )

    implicit val jsonDecoder: Decoder[Json] =
      decoder((index, row, session) => {
        // 这里就不处理失败的场景
        parser
          .parse(
            row.getObject(index, classOf[org.postgresql.util.PGobject]).getValue
          )
          .getOrElse(Json.fromJsonObject(JsonObject.empty))
      })

    implicit class JsonBOps(left: Json) {
      def :||(right: Json) = quote(
        infix"\${left} || \${right}::jsonb".as[Json]
      )
    }

    implicit class InstantOps(dt: Instant) {

      val > = quote { (right: Instant) =>
        infix"\$dt > \$right".as[Boolean]
      }

      val >= = quote { (right: Instant) =>
        infix"\$dt >= \$right".as[Boolean]
      }

      val < = quote { (right: Instant) =>
        infix"\$dt < \$right".as[Boolean]
      }

      val <= = quote { (right: Instant) =>
        infix"\$dt <= \$right".as[Boolean]
      }

    }

    implicit class LocalDateTimeOps(left: LocalDateTime) {

      val :> = quote { (right: LocalDateTime) =>
        infix"\$left > \$right".as[Boolean]
      }

      val :>= = quote { (right: LocalDateTime) =>
        infix"\$left >= \$right".as[Boolean]
      }

      val :< = quote { (right: LocalDateTime) =>
        infix"\$left < \$right".as[Boolean]
      }

      val :<= = quote { (right: LocalDateTime) =>
        infix"\$left <= \$right".as[Boolean]
      }

    }
  }

}
