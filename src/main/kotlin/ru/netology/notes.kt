package ru.netology

open class Note<A, B>(
    var text: A,
    var title: A,
    var noteId: B

) {
    override fun equals(other: Any?): Boolean {
        other as Note<*, *>
        if (text != other.text) return false
        if (title != other.title) return false
        if (noteId != other.noteId) return false
        return true
    }

}

class Comment<A, B>(
    text: A,
    title: A,
    noteId: B,
    var message: A,
    val commentId: B,
) : Note<A, B>(text, title, noteId) {
    override fun equals(other: Any?): Boolean {
        other as Comment<*, *>
        if (text != other.text) return false
        if (title != other.title) return false
        if (noteId != other.noteId) return false
        if (message != other.message) return false
        if (commentId != other.commentId) return false
        return true
    }
}

class NoteNotFoundException(message: String) : RuntimeException(message)
class CommentNotFoundException(message: String) : RuntimeException(message)


object NoteService {
    private var notes = mutableListOf<Note<Any, Any>>()
    private var comments = mutableListOf<Comment<Any, Any>>()
    private var notesLastId = 0
    private var commentsLastId = 0
    private var commentsWaste = mutableListOf<Comment<Any, Any>>()
    fun clean() {
        notes = mutableListOf<Note<Any, Any>>()
        comments = mutableListOf<Comment<Any, Any>>()
        notesLastId = 0
        commentsLastId = 0
        commentsWaste = mutableListOf<Comment<Any, Any>>()
    }


    fun notesAdd(text: Any): Any {
        notes.add(Note(text = text, title = text, noteId = ++notesLastId))
        return notesLastId
    }

    fun notesCreateComment(noteId: Any, message: Any, commentId: Int = 0): Any {
        notes.forEach {
            if (it.noteId == noteId) {
                comments.add(
                    Comment(
                        text = it.text, title = it.title,
                        noteId = noteId, message = message, commentId = ++commentsLastId
                    )
                )
                return commentsLastId
            }
        }
        throw NoteNotFoundException("Note with noteID $noteId not found")
    }

    fun notesDelete(noteId: Any): Any {

        if (!notes.removeIf { it.noteId == noteId }) {
            throw NoteNotFoundException("Note with noteID $noteId not found")
        }
        comments.removeIf { it.noteId == noteId }
        return 1

    }

    fun notesDeleteComment(commentId: Any): Any {
        comments.forEach {
            if (it.commentId == commentId) {
                commentsWaste.add(it)
                comments.remove(it)
                return 1
            }

        }

        throw CommentNotFoundException("Comment with commentID $commentId not found")
    }

    fun notesEdit(noteId: Any, title: Any, text: Any): Any {

        notes.forEach {
            if (it.noteId == noteId) {
                it.title = title
                it.text = text
                return 1
            }
        }
        throw NoteNotFoundException("Note with noteID $noteId not found")
    }

    fun notesEditComment(commentId: Any, message: Any): Any {
        comments.forEach {
            if (it.commentId == commentId) {
                it.message = message
                return 1
            }
        }
        throw CommentNotFoundException("Comment with commentId $commentId not found")
    }

    fun notesGet(noteIds: Set<Any> = setOf()): List<Note<Any, Any>> {
        val notesGet = notes.filter { it.noteId in noteIds }

        if (notesGet.size !== noteIds.size) {
            throw NoteNotFoundException("Some Notes not found")
        }
        return notesGet
    }

    fun notesGetById(noteId: Any): Note<Any, Any> {
        notes.forEachIndexed { index, note ->
            if (note.noteId == noteId) {
                return notes[index]
            }
        }
        throw NoteNotFoundException("Note with noteId $noteId not found")
    }

    fun notesGetComments(noteId: Any): Array<Comment<Any, Any>> {
        var notesGetComments = emptyArray<Comment<Any, Any>>()
        comments.forEachIndexed { index, comment ->
            if (comment.noteId == noteId) {
                notesGetComments += comments[index]
            }
        }
        if (notesGetComments.isEmpty()) {
            throw NoteNotFoundException("Note with noteID $noteId not found")
        }
        return notesGetComments
    }

    fun notesGetFriendsNotes(offset: Set<Any>): List<Note<Any, Any>> {
        // TODO if necessary
        return emptyList()
    }

    fun notesRestoreComment(commentId: Any): Any {
        commentsWaste.forEach {
            if (it.commentId == commentId) {
                comments.add(it)
                commentsWaste.remove(it)
                return 1
            }
        }
        throw CommentNotFoundException("Comment with commentId $commentId not found")
    }


    fun printNotes() {
        println()
        println("Notes:")
        for ((index) in notes.withIndex()) {
            println("${notes[index].text}, ${notes[index].noteId}")
        }
    }

    fun printComments() {
        println()
        println("Comments:")
        for ((index) in comments.withIndex()) {
            println(
                "commentID- ${comments[index].commentId}, NoteId-${comments[index].noteId}, " +
                        "Note - ${comments[index].text}, Comment - ${comments[index].message}"
            )
        }
    }

    fun printCommentsWaste() {
        println()
        println("Comments waste:")
        commentsWaste.forEach {
            println(
                "commentID- ${it.commentId}, noteId - ${it.noteId} Note -" +
                        " ${it.text}, Comment - ${it.message}"
            )
        }
    }


}


