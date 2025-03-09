/** @format */

function formatDateTime(inputDate) {
  const date = new Date(inputDate);
  const year = date.getFullYear();
  const month = date.getMonth() + 1; // 월은 0부터 시작하므로 1을 더해줍니다.
  const day = date.getDate();
  const hours = date.getHours();
  const minutes = date.getMinutes();

  const formattedDate = `${year} / ${month} / ${day} ${hours}시 ${minutes}분`;

  return formattedDate;
}

export default formatDateTime;
