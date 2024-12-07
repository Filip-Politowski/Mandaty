const toggleFormChange = () => {
    let searchType = document.getElementById("search-type").value;
    const nameForm = document.getElementById("form-name");
    const signatureForm = document.getElementById("form-signature");
    const phoneForm = document.getElementById("phone-form")
    if (searchType === "name") {
        nameForm.style.display = "block";
        signatureForm.style.display = "none";
        phoneForm.style.display = "none";
    } else if (searchType === "signature") {
        nameForm.style.display = "none";
        signatureForm.style.display = "block";
        phoneForm.style.display = "none";
    } else if (searchType === "phone") {
        nameForm.style.display = "none";
        signatureForm.style.display = "none";
        phoneForm.style.display = "block";
    }
}
const clearFilters = () => {
    const form = document.querySelector("form");

    const inputs = form.querySelectorAll("input, select");

    inputs.forEach(input => {
        if (input.type === "text" || input.type === "date") {
            input.value = "";
        } else if (input.tagName === "SELECT") {
            input.value = "";
        }
    });
}

window.onload = function () {
    toggleFormChange()
};