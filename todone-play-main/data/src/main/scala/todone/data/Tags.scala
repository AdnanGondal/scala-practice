package todone.data

import io.circe.Codec
import io.circe.generic.semiauto._

final case class Tags(tags: List[Tag]) {
  def remove(tag: Tag): Tags =
    Tags(tags.filter(t => t != tag))

  def contains(tag: Tag): Boolean =
    tags.contains(tag)

  def append(tag: Tag): Tags =
    Tags(tags :+ tag)
}
object Tags {
  val empty = Tags(List.empty)

  implicit val tagsCodec: Codec[Tags] = deriveCodec[Tags]
}
