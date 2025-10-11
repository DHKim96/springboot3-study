// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        // fetch 로 delete 요청
        fetch(`/api/articles/${id}`, {
            method: 'DELETE'
        })
        .then(() => { // fetch() 가 잘 완료되면 연이어 실행되는 메서드
            alert("삭제가 완료되었습니다.");
            location.replace('/articles') // 실행 시 사용자의 웹브라우저 화면을 현재 주소를 기반해 옮겨주는 역할
        });
    });
}

// 수정 기능
// 1. id 가 modify-btn 인 엘리먼트 조회
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    // 2. 클릭 이벤트 감지 시 수정 api 요청
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search); // 현재 url 을 가져오는
        let id = params.get('id'); // 쿼리 파라미터를 가져옴

        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: { // header 아님!
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
        .then(() => {
            alert("수정이 완료되었습니다.")
            location.replace(`/articles/${id}`);
        });
    });
}

// 등록 기능
// 1. id 가 create-btn 인 엘리먼트
const createButton = document.getElementById('create-btn');

if (createButton) {
    // 2. 클릭 이벤트 감지 시 생성 api 요청
    createButton.addEventListener('click', event => {
        fetch(`/api/articles`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        }).then(() => {
            alert("등록되었습니다.");
            location.replace("/articles");
        });
    });
}