function directOrderedReports(){

    if(location.href.match("asc")){
        location.href = "/reports/desc";
    }
    else if(location.href.match("desc")){
        location.href = "/reports";
    }
    else {
        location.href = "/reports/asc";
    }
}