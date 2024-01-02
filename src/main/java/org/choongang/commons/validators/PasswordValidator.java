package org.choongang.commons.validators;

public interface PasswordValidator {


    /**
     * 비밀번호에 알파벳 포함여부
     * @param passwprd
     * @param caseIncensitive
     *      false : 대문자 1개 이상, 소문자 1개 이상 포함
     *      true : 대소문자 구분없이 1개 이상 포함
     * @return
     */
    default boolean alphaCheck(String passwprd, boolean caseIncensitive) {

    }

    /**
     * 비밀번호에 숫자 포함여부
     * @param password
     * @return
     */
    default boolean numberCheck(String password){

    }

    /**
     * 비밀번호에 특수문자 포함여부
     * @param password
     * @return
     */
    default boolean specialcharsCheck(String password){

    }
}
