<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="게시판 - 목록" />
<%@ include file="/WEB-INF/views/layout/header.jspf" %>

<div class="row">
    <h1 class="h1">게시글 목록</h1>
    <a class="btn btn--primary" href="${pageContext.request.contextPath}/posts/write">
        글쓰기
    </a>
</div>

<table class="table">
    <thead>
    <tr>
        <th style="width:90px;">번호</th>
        <th>제목</th>
        <th style="width:180px;">작성일</th>
    </tr>
    </thead>

    <tbody>
    <c:choose>
        <c:when test="${empty posts}">
            <tr>
                <td colspan="3" class="meta">아직 게시글이 없어요.</td>
            </tr>
        </c:when>

        <c:otherwise>
            <c:forEach var="p" items="${posts}">
                <tr>
                    <td>${p.id}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/posts/detail?id=${p.id}">
                            <c:out value="${p.title}" />
                        </a>
                    </td>
                    <td class="meta">
                        <c:out value="${p.createdAt}" />
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>

<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
