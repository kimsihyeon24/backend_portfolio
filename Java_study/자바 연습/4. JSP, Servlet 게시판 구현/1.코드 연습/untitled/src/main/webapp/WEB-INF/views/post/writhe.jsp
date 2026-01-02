<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<c:set var="pageTitle" value="게시판 - 글쓰기" />

<div class="card">
    <div class="row">
        <h1 class="h1">글쓰기</h1>
        <a class="btn btn--ghost" href="${pageContext.request.contextPath}/board/list">목록</a>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/board/write">
        <div style="margin-bottom:12px;">
            <div class="meta" style="margin-bottom:8px;">제목</div>
            <input class="input" name="title" placeholder="제목을 입력하세요" required />
        </div>

        <div style="margin-bottom:14px;">
            <div class="meta" style="margin-bottom:8px;">내용</div>
            <textarea class="textarea" name="content" placeholder="내용을 입력하세요" required></textarea>
        </div>

        <div class="row" style="justify-content:flex-end;">
            <button class="btn btn--primary" type="submit">등록</button>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
