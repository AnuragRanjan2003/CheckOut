package com.example.checkout.others.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class AuthUtilsTest{

    @Test
    fun `email empty`(){
        val result = AuthUtils.verifyCredentialsLengths("","123456")
        assertThat(result== EMAIL_EMPTY).isTrue()
    }

    @Test
    fun `pass empty`(){
        val result = AuthUtils.verifyCredentialsLengths("abc@gmail.com","")
        assertThat(result== PASS_EMPTY).isTrue()
    }

    @Test
    fun `email invalid`(){
        val result = AuthUtils.verifyCredentialsLengths("abc","123456")
        assertThat(result== EMAIL_INVALID).isTrue()
    }

    @Test
    fun `password short`(){
        val result = AuthUtils.verifyCredentialsLengths("abc@gmail.com","123")
        assertThat(result== PASS_SHORT).isTrue()
    }

    @Test
    fun `email empty msg`(){
        val result = AuthUtils.verifyCredentialsLengths("","123456")
        assertThat(AuthUtils.getMessage(result)).matches(MSG_EMAIL_EMPTY)
    }

    @Test
    fun `pass empty msg`(){
        val result = AuthUtils.verifyCredentialsLengths("abc@gmail.com","")
        assertThat(AuthUtils.getMessage(result)).matches(MSG_PASS_EMPTY)
    }

    @Test
    fun `email invalid msg`(){
        val result = AuthUtils.verifyCredentialsLengths("abc","123456")
        assertThat(AuthUtils.getMessage(result)).matches(MSG_EMAIL_INVALID)
    }

    @Test
    fun `password short msg`(){
        val result = AuthUtils.verifyCredentialsLengths("abc@gmail.com","123")
        assertThat(AuthUtils.getMessage(result)).matches(MSG_PASS_SHORT)
    }

    @Test
    fun `ok msg`(){
        val res = AuthUtils.verifyCredentialsLengths("abc@gmail.com","123456")
        assertThat(AuthUtils.getMessage(res)).matches("")
    }

    @Test
    fun `is null Uid`(){
        val s = null
        val res = AuthUtils.isUid(s)
        assertThat(res).isFalse()
    }

    @Test
    fun `blank uid`(){
        val s = ""
        val res = AuthUtils.isUid(s)
        assertThat(res).isFalse()
    }
    @Test
    fun `message uid`(){
        val s = "There is no user record corresponding to this identifier. The user may have been deleted."
        val res = AuthUtils.isUid(s)
        assertThat(res).isFalse()
    }
    @Test
    fun `message uid 2`(){
        val s = "There is no user record corresponding to this identifier"
        val res = AuthUtils.isUid(s)
        assertThat(res).isFalse()
    }

}