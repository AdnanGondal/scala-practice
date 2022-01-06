import cats.data.NonEmptyList

         // head element,  //list as a tail
NonEmptyList(1, List(2, 3))
// uses apply;

NonEmptyList.fromList(List(1, 2, 3))

val nel = NonEmptyList.of(1, 2, 3)

nel.head

nel.tail
