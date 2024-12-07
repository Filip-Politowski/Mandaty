function handleFileChange() {
    const fileInput = document.getElementById('file');
    const label = document.querySelector('.file-upload-label');
    const fileNameParagraph = document.getElementById('file-name');


    fileNameParagraph.style.display = 'block';
    fileNameParagraph.textContent = fileInput.files[0].name;
    label.textContent = 'Select other file';

}

const cancelForm = () => {
    let form = document.getElementById("myForm");
    const physicalEmployeeFields = document.getElementById("physicalEmployeeFields");
    const officeEmployeeFields = document.getElementById("officeEmployeeFields");
    const otherField = document.getElementById("customViolationReasonDiv");
    const fileInput = document.getElementById('file');
    const label = document.querySelector('.file-upload-label');
    const fileNameParagraph = document.getElementById('file-name');

    fileNameParagraph.style.display = "none";
    fileNameParagraph.textContent = "";
    label.textContent = "Add PDF file";
    otherField.style.display = "none";
    physicalEmployeeFields.style.display = "block";
    officeEmployeeFields.style.display = "none";
    form.reset();
}


const toggleEmployeeFields = () => {
    let employeeType = document.getElementById("employeeType").value;
    const physicalEmployeeFields = document.getElementById("physicalEmployeeFields");
    const officeEmployeeFields = document.getElementById("officeEmployeeFields");
    if (employeeType === "PHYSICAL") {
        physicalEmployeeFields.style.display = "block";
        officeEmployeeFields.style.display = "none";
    } else if (employeeType === "OFFICE") {
        physicalEmployeeFields.style.display = "none";
        officeEmployeeFields.style.display = "block";
    }
}


const toggleOtherField = () => {
    const select = document.getElementById("violationReason");
    let otherOption = select.value === "OTHER";
    const otherField = document.getElementById("customViolationReasonDiv");
    otherField.style.display = otherOption ? "block" : "none";
}

window.onload = function () {
    toggleEmployeeFields();
    toggleOtherField();
};