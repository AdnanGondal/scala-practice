package models

import javax.inject._

import scala.collection.concurrent

import todone.data._

@Singleton
class TasksModel() {
  //  concurrent safe access...
  val currentId = new java.util.concurrent.atomic.AtomicInteger(5)
  val tasksStore = concurrent.TrieMap[Id, Task](
    Id(1) -> Task(
      State.open,
      "Play with the ToDone interface",
      """Right now many things in the application are not working.
        |Our task is to make it work, and the first step to that is
        |finding what is needs to be fixed.""".stripMargin,
      Some(Project("todone")),
      Tags(List(Tag("scalabridge"), Tag("frontend")))
    ),
    Id(2) -> Task(
      State.open,
      "Learn how to use the web developer tools",
      """The web developer tools are one of the most useful tools for
        |debugging problems between the frontend and backend. We need
        |open up the web developer tools and look at the network pane,
        |where we'll find requests from the frontend that the backend
        |is not properly responding to.""".stripMargin,
      Some(Project("todone")),
      Tags(List(Tag("scalabridge"), Tag("frontend")))
    ),
    Id(3) -> Task(
      State.open,
      "Implement functionality to close a completed task",
      """The close button is probably the simplest bit of functionality
        |we can implement. (Hopefully you discovered the close button
        |doesn't work.) Let's do that now! The worksheets have more
        |details.""".stripMargin,
      Some(Project("todone")),
      Tags(List(Tag("scalabridge"), Tag("backend")))
    ),
    Id(4) -> Task(
      State.open,
      "Have a break!",
      "Go to bed.",
      None,
      Tags(List(Tag("chillout")))
    )
  )

  def nextId(): Id = {
    val id = currentId.getAndIncrement()
    Id(id)
  }

  def tasks: Tasks = {
    val sortedTasks: List[(Id, Task)] =
      //tasksStore.toList
        // takes implicit ordering value that needs to be defined
      // FOR TYPE ID

      // partial function; a function that starts with a match / case
      // needs to be in curly braces
    tasksStore.toArray.sortInPlaceBy { case (id, _) => id }.toList
    Tasks(sortedTasks)
  }

  def tags: Tags = {
                                        // counter productive piece of code?
                                        // could we make this more readable?
                                        //
    //Tags(tasksStore.toArray.flatMap(elem => elem._2.tags.tags).toSet.toList)
    // pattern matching takes place, on single paramter,
    // break it into two elements that make up a tuple
    Tags(tasksStore.toArray.flatMap{ case (id, task) => task.tags.tags}.toSet.toList)
  }

  def projects: Projects = {
    Projects(tasksStore.toArray.map(elem => elem._2.project.getOrElse(Project("Not assigned"))).toSet.toList)
  }

  def create(task: Task): Id = {
    val id = nextId()
    tasksStore.addOne(id -> task)
    id
  }

}
