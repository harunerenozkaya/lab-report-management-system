function dropdownName(name){
    //Set button innertext is selected input type
    document.getElementById("navbarDropdown").innerText = name;

    //If input type is all reports then we shouldn't be able to use input bar
    if(name === "All Reports"){
        document.getElementById("search-input").disabled = true;
    }else{
        document.getElementById("search-input").disabled = false;
    }

    //If input type patient tc then it contains just numbers
    if(name === "Patient TC")
        $('#search-input').prop('type', 'number');
    else
        $('#search-input').prop('type', 'text');
}