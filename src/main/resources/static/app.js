let selectedMood = "HAPPY";
let lastSelectionId = null;

function selectMood(mood) {
    selectedMood = mood;

    document.querySelectorAll(".moods button").forEach(button => {
        button.classList.remove("active");
    });

    document.getElementById("mood-" + mood).classList.add("active");
}

async function register() {
    const body = {
        username: username.value,
        email: email.value,
        password: password.value
    };

    const response = await fetch("/api/registry/register", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(body)
    });

    showUserResult(response);
}

async function login() {
    const body = {
        username: username.value,
        password: password.value
    };

    const response = await fetch("/api/registry/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(body)
    });

    showUserResult(response);
}

async function logout() {
    await fetch("/api/registry/logout", {method: "POST"});
    userInfo.textContent = "Вы не вошли в систему";
}

async function showUserResult(response) {
    const data = await response.json();

    if (!response.ok || data.error) {
        userInfo.textContent = data.error || "Ошибка входа";
        return;
    }

    userInfo.textContent = "Вы вошли как: " + data.username;
}

async function createSelection() {
    selectionStatus.textContent = "Подбор выполняется...";
    movies.innerHTML = "";

    const response = await fetch("/api/selections", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({mood: selectedMood})
    });

    const data = await response.json();

    lastSelectionId = data.id;

    if (data.status === "COMPLETED") {
        selectionStatus.textContent = "Подбор завершён";
    } else {
        selectionStatus.textContent = "Ошибка: " + data.errorMessage;
    }

    renderMovies(data.movies || []);
    loadHistory();
}

function renderMovies(movieList) {
    movies.innerHTML = "";

    if (movieList.length === 0) {
        movies.innerHTML = "<p>Фильмы не найдены.</p>";
        return;
    }

    movieList.forEach(movie => {
        const poster = movie.posterPath
            ? "https://image.tmdb.org/t/p/w500" + movie.posterPath
            : "";

        const card = document.createElement("div");
        card.className = "movie-card";

        card.innerHTML = `
            ${poster ? `<img src="${poster}" alt="${movie.title}">` : ""}
            <div class="movie-card-content">
                <h3>${movie.title}</h3>
                <p><b>Дата выхода:</b> ${movie.releaseDate || "неизвестно"}</p>
                <p><b>Оценка:</b> ${movie.voteAverage || "нет"}</p>
                <p>${movie.overview || "Описание отсутствует."}</p>
            </div>
        `;

        movies.appendChild(card);
    });
}

async function loadHistory() {
    const historyBlock = document.getElementById("history");

    const response = await fetch("/api/selections");
    const data = await response.json();

    if (!response.ok || data.error) {
        historyBlock.innerHTML = "<p>" + (data.error || "История недоступна") + "</p>";
        return;
    }

    historyBlock.innerHTML = "";

    if (data.length === 0) {
        historyBlock.innerHTML = "<p>История пока пустая.</p>";
        return;
    }

    data.forEach(item => {
        const div = document.createElement("div");
        div.className = "history-item";

        div.innerHTML = `
            <b>Подборка #${item.id}</b><br>
            Настроение: ${item.moodTitle}<br>
            Статус: ${item.status}<br>
            Сохранена: ${item.saved ? "да" : "нет"}<br>
            <button onclick="openSelection(${item.id})">Открыть</button>
            <button onclick="saveSelection(${item.id})">Сохранить</button>
        `;

        historyBlock.appendChild(div);
    });
}

async function openSelection(id) {
    const response = await fetch("/api/selections/" + id);
    const data = await response.json();

    selectionStatus.textContent = "Открыта подборка #" + data.id;
    renderMovies(data.movies || []);
}

async function saveSelection(id) {
    const response = await fetch("/api/selections/" + id + "/save", {
        method: "POST"
    });

    const data = await response.json();

    if (data.error) {
        alert(data.error);
        return;
    }

    loadHistory();
}

selectMood("HAPPY");
