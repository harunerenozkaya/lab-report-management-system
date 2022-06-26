function directSearchReports(){
    const searchType = document.getElementById("navbarDropdown").innerText;
    const searchInput = document.getElementById("search-input").value;
    const searchParams = new URLSearchParams(location.href);

    //If searchType is All Reports then direct all reports page
    if(searchType === "All Reports")
        location.href = "/reports";

    else{
        switch (searchType){
            case "Patient TC":
                if(searchParams.has("searchType"))
                    searchParams.set("searchType","patientTC");
                else
                    searchParams.append("searchType","patientTC");

                break;

            case "Patient Name Surname":
                if(searchParams.has("searchType"))
                    searchParams.set("searchType","patientNS");
                else
                    searchParams.append("searchType","patientNS");
                break;

            case "Laborant Name Surname":
                if(searchParams.has("searchType"))
                    searchParams.set("searchType","laborantNS");
                else
                    searchParams.append("searchType","laborantNS");
                break;
        }

        if(searchParams.has("searchInput"))
            searchParams.set("searchInput",searchInput);
        else
            searchParams.append("searchInput",searchInput);


        const urlParts = searchParams.toString().split('=&');

        location.href = "/reports?&" + urlParts[1];
    }

}