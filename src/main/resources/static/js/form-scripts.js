function toggleEmployeeFields() {
    var employeeType = document.getElementById("employeeType").value;
    var physicalEmployeeFields = document.getElementById("physicalEmployeeFields");
    var officeEmployeeFields = document.getElementById("officeEmployeeFields");

    if (employeeType === "PHYSICAL") {
        physicalEmployeeFields.style.display = "block";
        officeEmployeeFields.style.display = "none";
    } else if (employeeType === "OFFICE") {
        physicalEmployeeFields.style.display = "none";
        officeEmployeeFields.style.display = "block";
    }
}


function toggleOtherField() {
    var select = document.getElementById("violationReason");
    var otherOption = select.value === "OTHER";
    var otherField = document.getElementById("customViolationReasonDiv");
    otherField.style.display = otherOption ? "block" : "none";
}
window.onload = toggleEmployeeFields;

