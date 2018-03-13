package com.online_examing;

public interface Routes {

    /**
     * Comment
     */
    String COMMENT_BASE_ROUTE = "/comment";
    String COMMENT_ADD = "/commentAdd";
    String COMMENT_GET_BY_ID = "/getByComment";
    String COMMENT_GET_ALL = "/getAllComment";
    String COMMENT_SEARCH = "/search";
    String COMMENT_UPDATE_SELECT_COMMENT = "/updateSelectComment";

    /**
     * User
     */
    String USER_BASE_ROUTE = "/user";
    String USER_LOGIN = "/login";
    String USER_REGISTER = "/register";
    String USER_UPDATEINFO = "/updateInfo";
    String USER_GETINFO = "/getInfo";
    String USER_GET_PAPER = "/getPaper";


    /**
     * Paper
     */
    String PAPER_BASE_ROUTE = "/paper";
    String PAPER_ADDQUESTION = "/addQuestion";
    String PAPER_GETQUESTIONDSIZE = "/getQuestionSize";
    String PAPER_GETQUESTIONDLIST = "/getQuestionList";
    String PAPER_SEARCHQUESTION = "/searchQuestion";
    String PAPER_UPDATEQUESTION = "/updateQuestion";
    String PAPER_DELETEQUESTION = "/deleteQuestion";

    /**
     * Exam
     */
    String EXAM_BASE_ROUTE = "/exam";
    String EXAM_CREATE_UPDATE = "/createOrUpdatePaper";
    String EXAM_GET = "/getPaper";
    String EXAM_GET_PAPEER_BY_ID = "/getPaperById";
    String EXAM_ADD_PAPER_ANSWER = "/addPaperAnswer";
    String EXAM_EXIST_PAPER_ANSWER = "/existPaperAnswer";
}
