const reviews = [
    {
        productId: 'gobdoritang',
        nickname: 'a',
        profile: "../img/gobdoritang.png",
        date: '2024-09-25',
        rating: 4,
        text: '진짜 너무 너무 맛있어요! 제 식사 스콘은 모네랑 바스크입니다! 색깔이랑 치즈가 최애예요! 다음에는 치즈맛 구매하려고요! 기회되시면 비트맛도 나오면 좋을 것 같아요!',
        product: '맛있딜리, 색색브리 (7개)',
        reviewImg: "../img/gobdoritang.png"
    },
    {
        productId: 'gobdoritang',
        nickname: 'b',
        profile: "../img/gobdoritang.png",
        date: '2024-09-24',
        rating: 5,
        text: '좋은 맛이었어요! 하지만 조금 비쌌어요.',
        product: '맛있딜리, 초코브리 (5개)',
        reviewImg: "../img/gobdoritang.png"
    },
    {
        productId: 'gobdoritang',
        nickname: 'c',
        profile: "../img/gobdoritang.png",
        date: '2024-09-23',
        rating: 3,
        text: '괜찮은 맛, 하지만 기대에 미치지 못했어요.',
        product: '맛있딜리, 과일브리 (6개)',
        reviewImg: "../img/gobdoritang.png"
    }
];

function displayReviews(sortedReviews) {
    const reviewsContainer = document.getElementById('reviews');
    reviewsContainer.innerHTML = '';

    sortedReviews.forEach(review => {
        const reviewHtml = `
            <div class="review_header">
                <img src="${review.profile}" alt="프로필 이미지" class="profile">
                <div>${review.nickname}</div>
            </div>
            <span class="rating">${'⭐️'.repeat(review.rating)}</span>
            <span>${review.date}</span>
            <div class="review_text">
                <p>${review.text}</p>
                <img src="${review.reviewImg}" alt="상품 이미지" class="product_image">
            </div>
            <div class="footer">
                <p>구매상품: ${review.product}</p>
            </div>
            <hr>
        `;
        reviewsContainer.innerHTML += reviewHtml;
    });
}

function sortReviews(standard) {
    let sortedReviews;
    if (standard === 'latest') {
        sortedReviews = reviews.sort((a, b) => new Date(b.date) - new Date(a.date));
    } 
    else if (standard === 'highRate') {
        sortedReviews = reviews.sort((a, b) => b.rating - a.rating);
    } 
    else if (standard === 'lowRate') {
        sortedReviews = reviews.sort((a, b) => a.rating - b.rating);
    }
    displayReviews(sortedReviews);
}

// 초기 리뷰 표시
displayReviews(reviews);