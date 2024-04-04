package ru.netology

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NoteServiceTest<A, B> {
    @Before
    fun clearBeforeTest() {
        NoteService.clean()
    }

    @Test
    fun notesAdd_checkReturnNoteId() {
        val result = NoteService.notesAdd("Hello")
        assertEquals(1, result)
    }

    @Test
    fun notesAdd_checkResult() {
        NoteService.notesAdd("Hello")
        val result = NoteService.notesGet(setOf(1))
        val expected = listOf(Note("Hello", "Hello", 1))

        assertEquals(expected, result)
    }

    @Test
    fun notesCreateComment_checkReturnCommentId() {
        NoteService.notesAdd(1)
        val result = NoteService.notesCreateComment(1, "Int")
        assertEquals(result, 1)
    }

    @Test
    fun notesCreateComment_checkResult() {
        NoteService.notesAdd("hello")
        NoteService.notesCreateComment(1, "ok")
        val result = NoteService.notesGetComments(1)
        val expected = arrayOf(Comment("hello", "hello", 1, "ok", 1))

        assertEquals(true, expected.contentEquals(result))
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesCreateComment_shouldThrowNoteNotFoundException() {
        NoteService.notesAdd("Hello")
        NoteService.notesCreateComment(2, "Bye")

    }


    @Test
    fun notesDelete_checkReturn() {
        NoteService.notesAdd(1)
        val result = NoteService.notesDelete(1)
        assertEquals(result, 1)
    }

    @Test
    fun notesDelete_checkResult() {
        NoteService.notesAdd(1)
        NoteService.notesAdd(2)
        NoteService.notesDelete(1)
        val result = NoteService.notesGet(setOf(2))
        val expected = listOf(Note(2, 2, 2))
        assertEquals(expected, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesDelete_shouldThrowNoteNotFoundException() {
        NoteService.notesAdd("Hello")
        NoteService.notesDelete(2)

    }


    @Test
    fun notesDeleteComment_checkReturn() {
        NoteService.notesAdd("Hello")
        NoteService.notesCreateComment(1, "OK")
        NoteService.notesCreateComment(1, "OK")
        val result = NoteService.notesDeleteComment(2)
        assertEquals(1, result)
    }

    @Test
    fun notesDeleteComment_checkResult() {
        NoteService.notesAdd("Hello")
        NoteService.notesCreateComment(1, "OK")
        NoteService.notesCreateComment(1, "Okey")
        NoteService.notesDeleteComment(2)
        val result = NoteService.notesGetComments(1)
        val expected = arrayOf(Comment("Hello", "Hello", 1, "OK", 1))
        assertEquals(true, result.contentEquals(expected))
    }

    @Test(expected = CommentNotFoundException::class)
    fun notesDeleteComment_shouldThrowCommentNotFoundException() {
        NoteService.notesAdd("Hello")
        NoteService.notesCreateComment(1, "ok")
        NoteService.notesDeleteComment(2)

    }

    @Test
    fun notesEdit_checkReturn() {
        NoteService.notesAdd("Hello")
        val result = NoteService.notesEdit(1, "Hello", "Welcome")
        assertEquals(1, result)
    }

    @Test
    fun notesEdit_checkResult() {
        NoteService.notesAdd("Hello")
        NoteService.notesEdit(1, "Hello", "Welcome")
        val result = NoteService.notesGetById(1)
        val expected = Note("Welcome", "Hello", 1)
        assertEquals(expected, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesEdit_shouldThrowNoteNotFoundException() {
        NoteService.notesAdd("Hello")
        NoteService.notesEdit(2, 2, 2)

    }

    @Test
    fun notesEditComment_checkReturn() {
        NoteService.notesAdd("Hello")
        NoteService.notesCreateComment(1, "OK")
        val result = NoteService.notesEditComment(1, "okey")
        assertEquals(1, result)
    }

    @Test
    fun notesEditComment_checkResult() {
        NoteService.notesAdd("Hello")
        NoteService.notesCreateComment(1, "OK")
        NoteService.notesEditComment(1, "okey")
        val result = NoteService.notesGetComments(1)
        val expected = arrayOf(Comment("Hello", "Hello", 1, "okey", 1))
        assertEquals(true, result.contentEquals(expected))
    }

    @Test(expected = CommentNotFoundException::class)
    fun notesEditComment_shouldThrowCommentNotFoundException() {
        NoteService.notesAdd("Hello")
        NoteService.notesCreateComment(1, "ok")
        NoteService.notesEditComment(2, "okey")
    }

    @Test
    fun notesGet_checkReturn() {
        NoteService.notesAdd(1)
        NoteService.notesAdd(2)
        NoteService.notesAdd(3)
        val result = NoteService.notesGet(setOf(1, 3))
        val expected = listOf<Note<Any, Any>>(Note(1, 1, 1), Note(3, 3, 3))
        assertEquals(expected, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesGet_shouldThrowNoteNotFoundException() {
        NoteService.notesAdd(1)
        NoteService.notesGet(setOf(5))

    }

    @Test
    fun notesGetById_checkReturn() {
        NoteService.notesAdd("hello")
        NoteService.notesAdd("bye")
        val result = NoteService.notesGetById(1)
        val expected = Note("hello", "hello", 1)
        assertEquals(expected, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesGetById_shouldThrowNoteNotFoundException() {
        NoteService.notesAdd(1)
        NoteService.notesGetById(5)

    }

    @Test
    fun notesGetComments_checkReturn() {
        NoteService.notesAdd("Hello")
        NoteService.notesCreateComment(1, "ok")
        NoteService.notesCreateComment(1, "bye")
        val result = NoteService.notesGetComments(1)
        var expected = emptyArray<Comment<Any, Any>>()
        expected += Comment("Hello", "Hello", 1, "ok", 1)
        expected += Comment("Hello", "Hello", 1, "bye", 2)
        assertEquals(true, result.contentEquals(expected))

    }

    @Test(expected = NoteNotFoundException::class)
    fun notesGetComments_shouldThrowNoteNotFoundException() {
        NoteService.notesAdd(1)
        NoteService.notesCreateComment(1, 1)
        NoteService.notesGetComments(2)

    }


    @Test
    fun notesRestoreComment_checkReturn() {
        NoteService.notesAdd("hello")
        NoteService.notesCreateComment(1, "bye")
        NoteService.notesDeleteComment(1)
        val result = NoteService.notesRestoreComment(1)
        assertEquals(1, result)
    }

    @Test
    fun notesRestoreComment_checkResult() {
        NoteService.notesAdd("hello")
        NoteService.notesCreateComment(1, "bye")
        NoteService.notesDeleteComment(1)
        NoteService.notesRestoreComment(1)
        val result = NoteService.notesGetComments(1)
        val expected = arrayOf(Comment("hello", "hello", 1, "bye", 1))
        assertEquals(true, result.contentEquals(expected))
    }

    @Test(expected = CommentNotFoundException::class)
    fun notesRestoreComment_shouldThrowCommentNotFoundException() {
        NoteService.notesAdd("Hello")
        NoteService.notesCreateComment(1, "ok")
        NoteService.notesDeleteComment(1)
        NoteService.notesRestoreComment(2)
    }
}

