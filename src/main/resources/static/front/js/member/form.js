/**
 * 파일 업로드 후 후속 처리 함수
 *
 * @param files : 업로드 한 파일 정보 목록
 *
 * 올라온 파일경로가 이미지로 출력
 *
 */
function callbackFileUpload(files) {

    if (!files || files.length == 0) {
        return;
    }

    const file = files[0];

    let html = document.getElementById("image1_tpl").innerHTML;
    //console.log(html);

    // pop() 끝에 있는 것을 꺼넬때
    const imageUrl = file.thumbsUrl.length > 0 ? file.thumbsUrl.pop() : file.fileUrl;
    const seq = file.seq;

    html = html.replace(/\[seq\]/g, seq).replace(/\[imageUrl\]/g, imageUrl);

    const domParser = new DOMParser();
    const dom = domParser.parseFromString(html, "text/html");

    const imageTplEl = dom.querySelector(".image1_tpl_box");

    const profileImage = document.getElementById("profile_image");
    profileImage.innerHTML = "";

    profileImage.appendChild(imageTplEl);

}

/**
 * 파일삭제후 후속처리 함수
 *
 * @param seq : 파일 등록번호
 */
function callbackFileDelete(seq) {
    const fileEl = document.getElementById(`file_${seq}`);
    fileEl.parentElement.removeChild(fileEl);
}