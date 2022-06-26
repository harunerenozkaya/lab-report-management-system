function directOrderedReports(){
    const searchParams = new URLSearchParams(location.href);
    var sortNormal;

    /* Look for parameter count to show unsorted reports after decs style*/
    /*If there is only one parameter and deleting sorting parameter to unsorted style , it will cause error*/
    var matches = location.href.toString().match(/[a-z\d]+=[a-z\d]+/gi);
    var count = matches? matches.length : 0;

    //If orderType asc or desc already
    if(searchParams.has("orderType")){

        //If orderType is ascending then show descending
        if(searchParams.get("orderType") === "asc")
            searchParams.set("orderType","desc");

        //If orderType is descending then show unsorted
        else if(searchParams.get("orderType") === "desc"){
            if(count === 1)
                sortNormal = true;
            else
                searchParams.delete("orderType");
        }
    }
    //If orderType is null , show ascending
    else
        searchParams.append("orderType","asc");

    var urlParts = searchParams.toString().split('=&');

    if(sortNormal)
        location.href = "/reports";
    else
        location.href = "/reports?&" + urlParts[1];
}