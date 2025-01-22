// Example: Confirm deletion of attachments
document.addEventListener("DOMContentLoaded", function () {
    const deleteButtons = document.querySelectorAll('.btn-delete');
    deleteButtons.forEach(function (button) {
        button.addEventListener('click', function (event) {
            const confirmed = confirm("Are you sure you want to delete this attachment?");
            if (!confirmed) {
                event.preventDefault();
            }
        });
    });
});
