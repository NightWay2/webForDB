function clearError() {
    const errorDiv = document.getElementById('errorMessage');
    if (errorDiv) {
        errorDiv.style.display = 'none';
    }
}

function goToPage() {
            var pageNum = document.getElementById("pageInput").value;
            window.location.href = '/choose_option/choose_table/measurment_list/' + pageNum;
}