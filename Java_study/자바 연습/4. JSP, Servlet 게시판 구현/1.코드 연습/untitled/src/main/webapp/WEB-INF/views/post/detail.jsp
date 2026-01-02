<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<c:set var="pageTitle" value="게시판 - 상세" />

<div class="card">
    <div class="row">
        <div>
            <h1 class="h1"><c:out value="${board.title}" /></h1>
            <div class="meta">#${board.id} · ${board.createdAt}</div>
        </div>
        <a class="btn btn--ghost" href="${pageContext.request.contextPath}/board/list">목록</a>
    </div>

    <div style="margin-top:14px; white-space:pre-wrap; line-height:1.7;">
        <c:out value="${board.content}" />
    </div>

    <hr style="border:0; border-top:1px solid var(--line); margin:18px 0;" />

    <div class="row" style="justify-content:flex-end;">
        <form method="post" action="${pageContext.request.contextPath}/board/delete" style="margin:0;">
            <input type="hidden" name="id" value="${board.id}" />
            <button class="btn btn--danger" type="submit"
                    onclick="return confirm('정말 삭제할까요?');">삭제</button>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
